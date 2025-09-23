import { Component, Input } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { ProductModel } from '../models/product.model';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {
  @Input() product!: ProductModel;
}
