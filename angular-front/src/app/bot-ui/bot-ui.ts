import { Component, inject } from '@angular/core';
import {HttpClient, HttpDownloadProgressEvent, HttpEventType} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MarkdownModule } from 'ngx-markdown';
import {LoadingService} from '../services/loading';

@Component({
  selector: 'app-bot-ui',
  standalone: true,
  imports: [CommonModule, FormsModule, MarkdownModule],
  templateUrl: './bot-ui.html',
  styleUrl: './bot-ui.css'
})
export class BotUi {
  query: string = '';
  http = inject(HttpClient);
  response$!: Observable<any>;
  public loadingService = inject(LoadingService);

  askAgent() {
    if (!this.query) return;
    this.response$ = this.http.get(
      "http://localhost:9999/EBANK-BOT/chat?query=" + encodeURIComponent(this.query),
      { responseType: 'text' }
    );
  }
  askAgentStream() {
    this.response$ = this.http
      .get("http://localhost:9999/EBANK-BOT/chatStream?query=" + encodeURIComponent(this.query),
        { responseType: 'text' ,observe:'events',reportProgress: true })
      .pipe(map((event=> {
          switch (event.type){
            case HttpEventType.Sent:
              return {'type': 'sent'}
            case HttpEventType.DownloadProgress:
              return {type: 'Response',content: (event as HttpDownloadProgressEvent).partialText}
            case HttpEventType.Response:
              return {type: 'Response',content: event.body}
            default :
              return {type:'Other',data:event}
          }

        }))
      );
}}
