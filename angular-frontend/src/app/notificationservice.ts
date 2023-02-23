import { Injectable } from '@angular/core';


@Injectable({
    providedIn: 'root'
})
/**
 * Service that manages the permissions and creation of desktop notifications
 */
export class NotificationService {

    turnOnNotifications() {
        const notif = new Notification("Notifications enabled!", {
            body: "Stock notifications will now be sent!"
        });
    }

    notifyMe() {

        console.log(Notification.permission);

        if (Notification.permission == "granted") {
            this.turnOnNotifications();
        } else if (Notification.permission !== "denied") {
            Notification.requestPermission().then(permission => {
                if (Notification.permission == "granted") {
                    this.turnOnNotifications;
                }
            });
        }
    }

    createStockNotification(itemName: string, size: string) {
        const notif = new Notification("Item now in stock!", {
            body: itemName.concat(" in size ", size, " now in stock!")
        });
    }
}