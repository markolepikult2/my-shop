import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() currentPage: number;
  @Input() totalPages: number;
  @Input() first: boolean;
  @Input() last: boolean;

  @Output() pageChange = new EventEmitter<number>();

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.pageChange.emit(page);
    }
  }
}
