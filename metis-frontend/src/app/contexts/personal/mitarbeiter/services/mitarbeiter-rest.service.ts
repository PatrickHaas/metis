import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Mitarbeiter} from "../types/mitarbeiter.type";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class MitarbeiterRestService {

    constructor(private httpClient: HttpClient) {
    }

    findAll(): Observable<Mitarbeiter[]> {
        return this.httpClient.get<Mitarbeiter[]>(`http://localhost:8081/rest/v1/mitarbeiter`);
    }


    findById(id: string): Observable<Mitarbeiter> {
        return this.httpClient.get<Mitarbeiter>(`http://localhost:8081/rest/v1/mitarbeiter/${id}`);
    }
}
