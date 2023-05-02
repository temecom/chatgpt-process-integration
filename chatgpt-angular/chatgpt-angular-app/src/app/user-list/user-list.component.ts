import { Component } from '@angular/core';

interface Address {
  street: string;
  city: string;
  state: string;
  zip: string;
}

interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  address: Address;
}

@Component({
  selector: 'app-user-list',
  template: `
    <h1>Users:</h1>
    <ul>
      <li *ngFor="let user of users">
        {{ user.firstName }} {{ user.lastName }} - {{ user.email }}
        <div>{{ user.address.street }}</div>
        <div>{{ user.address.city }}, {{ user.address.state }} {{ user.address.zip }}</div>
      </li>
    </ul>
  `,
})
export class UserListComponent {
  users: User[] = [
    {
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      email: 'johndoe@example.com',
      address: {
        street: '123 Main St',
        city: 'Anytown',
        state: 'CA',
        zip: '12345',
      },
    },
    {
      id: 2,
      firstName: 'Jane',
      lastName: 'Doe',
      email: 'janedoe@example.com',
      address: {
        street: '456 Elm St',
        city: 'Othertown',
        state: 'NY',
        zip: '54321',
      },
    },
    {
      id: 3,
      firstName: 'Bob',
      lastName: 'Smith',
      email: 'bobsmith@example.com',
      address: {
        street: '789 Oak St',
        city: 'Somewhere',
        state: 'TX',
        zip: '67890',
      },
    },
  ];
}

