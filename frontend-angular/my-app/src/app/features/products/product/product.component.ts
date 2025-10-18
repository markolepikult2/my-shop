import { Component, inject, Input } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { ProductModel } from '../models/product.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {
  @Input() product!: ProductModel;
  private http = inject(HttpClient);

  addToCart(): void {
    const headers = new HttpHeaders({ 'X-User-Id': 'test-user' });
    const body = { productId: this.product.id, quantity: 1 };

    this.http.post('http://localhost:8080/basket/items', body, { headers })
      .subscribe({
        next: () => {
          console.log(`Product ${this.product.id} added to cart`);
          // Optionally, provide user feedback here (e.g., a toast notification)
        },
        error: (err) => {
          console.error('Failed to add product to cart', err);
        }
      });
  }

}
