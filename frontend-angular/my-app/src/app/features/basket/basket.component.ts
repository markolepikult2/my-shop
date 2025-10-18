import {Component, inject, OnInit, ChangeDetectorRef} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {forkJoin, of} from 'rxjs';
import {switchMap} from 'rxjs/operators';
import {ProductModel} from "../products/models/product.model";

// Interface for the raw item from the basket service
interface BasketItem {
    productId: number;
    quantity: number;
}

// Interface for the final, merged data structure
export interface FullBasketItem {
    productId: number;
    quantity: number;
    name: string;
    price: number;
}

@Component({
    selector: 'app-basket',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './basket.component.html',
    styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit {
    private http = inject(HttpClient);
    private cdr = inject(ChangeDetectorRef);

    loading = true;
    error?: string;
    fullBasketItems: FullBasketItem[] = [];
    totalPrice = 0;

    ngOnInit(): void {
        this.loadBasket();
    }

    private loadBasket(): void {
        this.loading = true;
        const headers = new HttpHeaders({'X-User-Id': 'test-user'});

        this.http.get<BasketItem[]>('http://localhost:8080/basket/items', {headers})
            .pipe(
                switchMap(basketItems => {
                    if (!basketItems || basketItems.length === 0) {
                        return of([]); // Return an empty observable stream if basket is empty
                    }

                    const productIds = basketItems.map(item => item.productId).join(',');
                    const products$ = this.http.get<ProductModel[]>(`http://localhost:8080/products/list?ids=${productIds}`);
                    // Combine basket items with product details
                    return forkJoin({basket: of(basketItems), products: products$});
                })
            )
            .subscribe({
                next: (result) => {
                    if (Array.isArray(result)) { // Handles the empty basket case
                        this.fullBasketItems = [];
                    } else {
                        this.fullBasketItems = result.basket.map(item => {
                            const product = result.products.find(p => p.id === item.productId);
                            return {...item, ...product};
                        });
                    }
                    this.calculateTotal();
                    this.loading = false;
                    this.cdr.detectChanges(); // Manually trigger change detection
                },
                error: (err) => {
                    this.error = 'Could not load basket. Is the API running?';
                    this.loading = false;
                    console.error(err);
                    this.cdr.detectChanges(); // Also trigger on error to show error message
                }
            });
    }

    private calculateTotal(): void {
        this.totalPrice = this.fullBasketItems.reduce((acc, item) => {
            return acc + (item.price * item.quantity);
        }, 0);
    }

    public deleteFromBasket(productId: number): void {
        const headers = new HttpHeaders({'X-User-Id': 'test-user'});

        this.http.delete(`http://localhost:8080/basket/items?productId=${productId}`, {headers})
            .subscribe({
                next: () => {
                    console.log(`Product ${productId} removed from cart`);
                    // Call loadBasket() here, AFTER the delete is successful
                    this.loadBasket();
                },
                error: (err) => {
                    console.error('Failed to delete product from cart', err);
                    this.error = `Failed to remove item ${productId}.`;
                    this.cdr.detectChanges(); // Show the error in the UI
                }
            });
    }
}
