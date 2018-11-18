import { NgModule } from '@angular/core';

import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule, MatOptionModule, MatSelectModule} from '@angular/material';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatListModule} from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatRadioModule} from '@angular/material/radio';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatTooltipModule} from '@angular/material';
import {MatMenuModule} from '@angular/material/menu';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatTabsModule} from '@angular/material/tabs';

const modules = [
  MatInputModule,
  MatToolbarModule,
  MatTableModule,
  MatDialogModule,
  MatCardModule,
  MatCheckboxModule,
  MatOptionModule,
  MatSelectModule,
  MatPaginatorModule,
  MatListModule,
  MatGridListModule,
  MatRadioModule,
  MatProgressSpinnerModule,
  MatSlideToggleModule,
  MatAutocompleteModule,
  MatTooltipModule,
	MatMenuModule,
	MatDatepickerModule,
	MatTabsModule
];

@NgModule({
  imports: modules,
  exports: modules,
})

export class MaterialModule { }
