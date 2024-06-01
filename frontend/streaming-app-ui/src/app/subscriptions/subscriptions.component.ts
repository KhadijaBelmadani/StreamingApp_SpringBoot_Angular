import { Component } from '@angular/core';
import {UserService} from "../user.service";

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent {
  userId: string = 'currentUserId';  // This should be set to the logged-in user's ID
  targetUserId!: string;
  subscribers: string[] = [];
  subscriptions: string[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadSubscribers();
    this.loadSubscriptions();
  }

  subscribe(): void {
    this.userService.subscribe(this.userId, this.targetUserId).subscribe(response => {
      console.log(response);
      this.loadSubscriptions();
    });
  }

  unsubscribe(): void {
    this.userService.unsubscribe(this.userId, this.targetUserId).subscribe(response => {
      console.log(response);
      this.loadSubscriptions();
    });
  }

  loadSubscribers(): void {
    this.userService.getSubscribers(this.userId).subscribe(data => {
      this.subscribers = data;
    });
  }

  loadSubscriptions(): void {
    this.userService.getSubscriptions(this.userId).subscribe(data => {
      this.subscriptions = data;
    });
  }
}
