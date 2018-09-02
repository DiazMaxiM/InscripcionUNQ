import { NgModule } from '@angular/core';

import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';


const modules = [
  MatInputModule,
  MatToolbarModule,
  MatTableModule,
  MatDialogModule,
  MatCardModule
];

@NgModule({
  imports: modules,
  exports: modules,
})

export class MaterialModule { }
