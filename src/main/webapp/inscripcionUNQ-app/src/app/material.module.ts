import { NgModule } from '@angular/core';

import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule, MatOptionModule, MatSelectModule} from '@angular/material';

const modules = [
  MatInputModule,
  MatToolbarModule,
  MatTableModule,
  MatDialogModule,
  MatCardModule,
  MatCheckboxModule,
  MatOptionModule,
  MatSelectModule
];

@NgModule({
  imports: modules,
  exports: modules,
})

export class MaterialModule { }
