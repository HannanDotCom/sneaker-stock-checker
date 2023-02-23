import { Component } from '@angular/core';
import { OnInit } from '@angular/core'
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { interval } from 'rxjs';

import { DesiredItem } from './desireditem';
import { LoadingIndicatorService } from './loadingservice';
import { DesiredItemService } from './desireditem.service';
import { NotificationService } from './notificationservice';

import { environment } from './environments/environments';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  public desiredItems: DesiredItem[] = [];
  public updatedDesiredItems: DesiredItem[] = [];
  public editDesiredItem: DesiredItem | undefined;
  public deleteDesiredItem: DesiredItem | undefined;

  constructor(private desiredItemService: DesiredItemService, private loadingService: LoadingIndicatorService, private notificationService: NotificationService) { }

  ngOnInit() {
    this.getDesiredItems();

    /** Fetches stock info from webpage at set interval */
    interval(environment.refreshTimeMins * 60 * 1000).subscribe(() => {
      this.updateStock();
    });

  }


  public updateStock(): void {

    /** First Http request to get all items */
    this.desiredItemService.getDesiredItems().subscribe(
      (response: DesiredItem[]) => {
        this.desiredItems = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )

    /** Second Http request to return updated stock list */
    this.desiredItemService.updateDesiredItemStock(this.desiredItems).subscribe(
      (response: DesiredItem[]) => {
        this.updatedDesiredItems = response;
        const index = this.checkStockChange(this.desiredItems, this.updatedDesiredItems);

        if (index < this.desiredItems.length && Notification.permission == "granted") {
          this.notificationService.createStockNotification(this.updatedDesiredItems[index].name, this.updatedDesiredItems[index].size);
        }
        this.desiredItems = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getDesiredItems(): void {
    this.desiredItemService.getDesiredItems().subscribe(
      (response: DesiredItem[]) => {
        this.desiredItems = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onOpenModal(mode: string, desireditem?: DesiredItem): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'add') {
      button.setAttribute('data-target', '#addDesiredItemModal');
    }
    if (mode === 'edit') {
      this.editDesiredItem = desireditem;
      button.setAttribute('data-target', '#updateDesiredItemModal');
    }
    if (mode === 'delete') {
      this.deleteDesiredItem = desireditem;
      button.setAttribute('data-target', '#deleteDesiredItemModal');
    }

    container?.appendChild(button);
    button.click();
  }

  public onAddItem(addForm: NgForm): void {
    document.getElementById('add-item-form')?.click();
    this.loadingService.onRequestStarted();
    this.desiredItemService.addDesiredItem(addForm.value).subscribe({
      next: (response: DesiredItem) => {
        console.log(response);
        this.getDesiredItems();
        this.loadingService.onRequestFinished();
        addForm.reset();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });

  }

  public onUpdateDesiredItem(desiredItem: DesiredItem): void {
    this.loadingService.onRequestStarted();
    this.desiredItemService.updateDesiredItem(desiredItem).subscribe({
      next: (response: DesiredItem) => {
        console.log(response);
        this.getDesiredItems();
        this.loadingService.onRequestFinished();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });

  }

  public onDeleteDesiredItem(desiredItemId: number): void {
    this.desiredItemService.deleteDesiredItem(desiredItemId).subscribe({
      next: (response: void) => {
        console.log(response);
        this.getDesiredItems();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });

  }

  /** 
   * Compares the item lists to check for a change in stock status
   * @param a original item list
   * @param b updated item list
   * @returns index of updated item if stock status has changed, length of item list otherwise
   */
  checkStockChange(a: DesiredItem[], b: DesiredItem[]) {

    for (var i = 0; i < a.length; i++) {
      if (a[i].inStock !== b[i].inStock) {
        return i;
      }
    }
    return i + 1;
  };

  getLoading() {
    return this.loadingService.loading();
  }

  notifyMe() {
    return this.notificationService.notifyMe();
  }

}
