import { Injectable } from '@angular/core';

// give credit
@Injectable({
    providedIn: 'root'
})
/**
 * Service that manages indication of start and end of a Http request
 */
export class LoadingIndicatorService {

    private _loading: boolean = false;

    loading(): boolean {
        return this._loading;
    }

    onRequestStarted(): void {
        this._loading = true;
    }

    onRequestFinished(): void {
        this._loading = false;
    }
}