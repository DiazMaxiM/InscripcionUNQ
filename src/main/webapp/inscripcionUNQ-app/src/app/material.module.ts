import { NgModule } from '@angular/core';

import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';


const modules = [
  MatInputModule,
  MatToolbarModule
];

@NgModule({
  imports: modules,
  exports: modules,
})

export class MaterialModule { }
