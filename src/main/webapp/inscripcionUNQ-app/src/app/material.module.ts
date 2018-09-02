import { NgModule } from '@angular/core';

import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';


const modules = [
  MatInputModule,
  MatToolbarModule,
  MatTableModule,
  MatDialogModule
];

@NgModule({
  imports: modules,
  exports: modules,
})

export class MaterialModule { }
