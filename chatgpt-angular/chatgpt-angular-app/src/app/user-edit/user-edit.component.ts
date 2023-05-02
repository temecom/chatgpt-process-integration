import { Component, Input, Output, EventEmitter } from '@angular/core';

import { User } from './user.model';

@Component({
  selector: 'app-user-edit',
  template: `
    <div>
      <label>First Name:</label>
      <input [(ngModel)]="user.firstName">
    </div>
    <div>
      <label>Last Name:</label>
      <input [(ngModel)]="user.lastName">
    </div>
    <div>
      <label>Email:</label>
      <input [(ngModel)]="user.email">
    </div>
    <div>
      <label>Street:</label>
      <input [(ngModel)]="user.address.street">
    </div>
    <div>
      <label>City:</label>
      <input [(ngModel)]="user.address.city">
    </div>
    <div>
      <label>State:</label>
      <input [(ngModel)]="user.address.state">
    </div>
    <div>
      <label>Zip:</label>
      <input [(ngModel)]="user.address.zip">
    </div>
    <button (click)="save()">Save</button>
    <button (click)="cancel()">Cancel</button>
  `,
})
export class UserEditComponent {
  @Input() user: User;
  @Output() saveUser = new EventEmitter<User>();
  @Output() cancelEdit = new EventEmitter<void>();

  save(): void {
    this.saveUser.emit(this.user);
  }

  cancel(): void {
    this.cancelEdit.emit();
  }
}

