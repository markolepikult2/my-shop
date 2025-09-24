import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ProductComponent } from '../product/product.component';
import { ProductListModel } from '../models/product-list.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, HttpClientModule, ProductComponent, RouterLink],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  private http = inject(HttpClient);
  productList: ProductListModel;
  loading = true;
  error?: string;

  ngOnInit() {
    // Try to load from API gateway; fall back to mock on error.
    this.http.get<ProductListModel>('http://localhost:8080/products').subscribe({
      next: (data) => {
          console.debug("Products loaded:", data);
          this.productList = data ?? null; this.loading = false;
          console.debug("Products loaded 2:", this.productList); },
      error: () => {
        this.loading = false;
        this.error = 'Showing demo data (API unreachable)';
        console.error("Failed to load products from API gateway");
      }
    });
  }
}
