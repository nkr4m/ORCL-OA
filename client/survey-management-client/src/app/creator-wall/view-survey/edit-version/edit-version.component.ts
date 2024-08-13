import { NotificationService } from './../../../core/service/notification.service';
import { AddSectionModalComponent } from './../add-section-modal/add-section-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { CreatorService } from './../../../core/service/creator.service';
import { Component, Input, OnInit, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
  CdkDrag,
  CdkDropList,
} from '@angular/cdk/drag-drop';
@Component({
  selector: 'app-edit-version',
  templateUrl: './edit-version.component.html',
  styleUrl: './edit-version.component.css'
})
export class EditVersionComponent {
  sections: any;
  @Input() version!: any; // Input property to receive data

  @Output() refreshVersion: EventEmitter<void> = new EventEmitter();

  userSubmissionResults!:any;

  constructor(private creatorService: CreatorService,
    public dialog: MatDialog, private notifier: NotificationService) { }

  ngOnInit(): void {
    // console.log('Survey details on init:', this.version);
    // Additional initialization logic here
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['version']) {
      const currentValue = changes['version'].currentValue;
      const previousValue = changes['version'].previousValue;
      // console.log('Survey details changed:', { previousValue, currentValue });
      this.sections = this.version?.sections
      if (this.version?.live == true) {
        this.fetchVersionResults()
      }
      
      // console.log(this.version?.surveyVersionId, 'versionid')
      // Handle changes here, e.g., update local state based on new values
    }
  }

  addSectionModal() {
    const dialogRef = this.dialog.open(AddSectionModalComponent, {
      data: {
        versionId: this.version,
      },
      width: '45%'
    });

    dialogRef.afterClosed().subscribe(result => {
      // Check if there is any data returned from the modal
      if (result && result.refreshed) {
        this.fetchAllSections(this.version.surveyVersionId);
        this.refreshVersion.emit();
      }
    });
  }

  fetchAllSections(versionId) {
    this.creatorService.viewAllVersionSection(versionId).subscribe({
      next: (data: any) => {
        this.sections = data;
      },
      error: (error) => {

      }
    })
  }



  drop(event: CdkDragDrop<string[]>) {
    if (this.version?.live == true) {
      return;
    }
    moveItemInArray(this.sections, event.previousIndex, event.currentIndex);
    // console.log(this.sections)
    this.creatorService.modifySections(this.sections, this.version.surveyVersionId).subscribe({
      next: (data) => {
        // console.log(data)
      },
      error: (error) => {

      }
    })
  }

  toggleVisibility() {
    // console.log(this.version)
    this.creatorService.modifyVersionStatus(this.version?.surveyVersionId).subscribe({
      next: (data) => {
        this.notifier.emitNotification('success', 'Survey status changed', 'Survey is live now please note we cannot edit the survey.')
        this.version = null;
        this.refreshVersion.emit();
      }
    })
  }

  fetchVersionResults() {
    // console.log(this.version, 'fetch results for')

    this.creatorService.fetchUserResults(this.version.surveyVersionId).subscribe({
      next:(data)=>{
        this.userSubmissionResults = data;
      },
      error:(error)=>{

      }
    })

  }




}
