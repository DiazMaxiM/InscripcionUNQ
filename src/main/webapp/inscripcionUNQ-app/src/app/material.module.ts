import { NgModule } from '@angular/core';

import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTableModule} from '@angular/material/table';

const modules = [
  MatInputModule,
  MatToolbarModule,
  MatTableModule,
];

@NgModule({
  imports: modules,
  exports: modules,
})

export class MaterialModule { }
