import { MatPaginatorIntl } from '@angular/material';
export class MatPaginatorI18n extends MatPaginatorIntl {
  itemsPerPageLabel = 'Items por página';
  nextPageLabel = 'Siguiente página';
  previousPageLabel = 'Página anterior';
  getRangeLabel = (page: number, pageSize: number, totalResults: number) => {
    if (!totalResults) { return 'Sin Resultados'; }
    totalResults = Math.max(totalResults, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex =
      startIndex < totalResults ?
        Math.min(startIndex + pageSize, totalResults) :
        startIndex + pageSize; return `${startIndex + 1} - ${endIndex} de  ${totalResults}`
      ;
  }
}
