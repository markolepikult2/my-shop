import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'products/add',
    loadComponent: () => import('./features/products/product-add/product-add.component').then(m => m.ProductAddComponent)
  },
  {
    path: 'products',
    loadComponent: () => import('./features/products/product-list/product-list.component').then(m => m.ProductListComponent)
  },
  {
    path: 'basket',
    loadComponent: () => import('./features/basket/basket.component').then(m => m.BasketComponent)
  },
  {
    path: '',
    redirectTo: 'products',
    pathMatch: 'full'
  }
];
