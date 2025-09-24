import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ProductComponent } from '../product/product.component';
import { ProductListModel } from '../models/product-list.model';
import { RouterLink } from '@angular/router';
import { PaginationComponent } from '../../../shared/pagination/pagination.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    ProductComponent,
    RouterLink,
    PaginationComponent
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  private http = inject(HttpClient);
  productList: ProductListModel;
  loading = true;
  error?: string;

  ngOnInit() {
    this.fetchProducts(0);
  }

  fetchProducts(page: number): void {
    this.loading = true;
    this.error = undefined;
    this.http.get<ProductListModel>(`http://localhost:8080/products?page=${page}`).subscribe({
      next: (data) => {
        this.productList = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'API is unreachable';
        console.error("Failed to load products from API gateway");
      }
    });
  }
}
