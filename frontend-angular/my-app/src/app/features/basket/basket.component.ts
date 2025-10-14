import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {forkJoin, map, Observable, of} from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ProductModel } from "../products/models/product.model";

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

  loading = true;
  error?: string;
  fullBasketItems: FullBasketItem[] = [];
  totalPrice = 0;

  ngOnInit(): void {
    this.loadBasket();
  }

  private loadBasket(): void {
    const headers = new HttpHeaders({ 'X-User-Id': 'test-user' });

    this.http.get<BasketItem[]>('http://localhost:8080/basket/items', { headers })
      .pipe(
        switchMap(basketItems => {
          if (!basketItems || basketItems.length === 0) {
            this.loading = false;
            return of([]); // Return an empty observable stream if basket is empty
          }

          const productIds = basketItems.map(item => item.productId).join(',');
          const products$ = this.http.get<ProductModel[]>(`http://localhost:8080/products/list?ids=${productIds}`);
          // Combine basket items with product details
          const  tempRet =  forkJoin({ basket: of(basketItems), products: products$ });
          console.log(tempRet);
          return tempRet;
        })
      )
      .subscribe({
        next: (result) => {
          if (Array.isArray(result)) { // Handles the empty basket case
            this.fullBasketItems = [];
          } else {
            this.fullBasketItems = result.basket.map(item => {
              console.log(item);
              const product = result.products.find(p => p.id === item.productId);
              return { ...item, ...product };
            });
            this.calculateTotal();
          }
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Could not load basket. Is the API running?';
          this.loading = false;
          console.error(err);
        }
      });
  }

  private calculateTotal(): void {
    this.totalPrice = this.fullBasketItems.reduce((acc, item) => {
      return acc + (item.price * item.quantity);
    }, 0);
  }
}
