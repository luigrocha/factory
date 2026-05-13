import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FORM_ERROR_MESSAGES } from 'src/app/core/constants/form-error';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CreateUser, GeneratedUsername, GenerateUsername, UpdateUser, User } from 'src/app/types/user.types';
import { UsersService } from 'src/app/core/http/users/users.service';
import { ShortPerson } from 'src/app/types/person.types';
import { PersonService } from 'src/app/core/http/persons/person.service';
import { EmailValidator } from 'src/app/shared/validators/user-email-validator';
import { AutoComplete } from 'primeng/autocomplete';

@Component({
  selector: 'app-user-modal',
  templateUrl: './user-modal.component.html',
  styleUrls: ['./user-modal.component.scss']
})
export class UserModalComponent implements OnInit {

  mode: 'create' | 'update' = 'create';

  form: FormGroup;
  formErrors = FORM_ERROR_MESSAGES;
  user: User;
  persons: ShortPerson[] = [];

  @ViewChild('personAuto', {static: true}) personAuto: AutoComplete;
  constructor(
    private fb: FormBuilder,
    public config: DynamicDialogConfig,
    public ref: DynamicDialogRef,
    private userService: UsersService,
    private personService: PersonService
  ) {
  }

  ngOnInit(): void {
    if (this.config.data) {
      this.user = this.config.data;
      this.mode = 'update';
    } else {
      this.user = {} as User;
      this.user.username = null;
    }

    this.form = this.fb.group({
      username: [{disabled: true, value: this.user.username}, [
        Validators.required,
        Validators.maxLength(32)
      ]],
      firstName: [{disabled: true, value: this.user.firstName}, [
        Validators.required,
        Validators.maxLength(32)
      ]],
      lastName: [{disabled: true, value: this.user.lastName}, [
        Validators.required,
        Validators.maxLength(32)
      ]],
      email: [this.user.email, [
        Validators.required,
        Validators.maxLength(50),
        Validators.email,
      ], EmailValidator.createValidator(this.userService)],
      password: [],
      person: [null, [
        Validators.required
      ]]
    });
    if (!this.isUpdateMode) {
      this.setPasswordValidators();
    } else {
      this.email.clearAsyncValidators();
      this.username.clearValidators();
      this.searchPerson(this.user.id);
    }
    this.changePerson();
  }

  setPasswordValidators() {
    this.password.setValidators([
      Validators.required,
      Validators.minLength(8)
    ]);
  }

  get username() {
    return this.form.get('username');
  }

  get firstName() {
    return this.form.get('firstName');
  }

  get lastName() {
    return this.form.get('lastName');
  }

  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }

  get person() {
    return this.form.get('person');
  }

  get isUpdateMode(): boolean {
    return this.mode === 'update';
  }

  generateUsername(firstName: string, lastName: string): void {
    if (!firstName || !lastName) {
      return;
    }
    this.userService.generateUsername({firstName, lastName} as GenerateUsername)
      .subscribe((res: GeneratedUsername) => {
          this.username.setValue(res.username);
        }
      );
  }

  save() {
    if (this.form.invalid) {
      return;
    }

    const user: CreateUser | UpdateUser = this.form.getRawValue();
    user.personId = this.person.value.id;

    this.ref.close(user);
  }

  close() {
    this.ref.close();
  }

  filterPersons($event: any) {
    const query = $event.query;
    if (query) {
      this.personService.search(query)
        .subscribe((res: ShortPerson[]) => {
          this.persons = res;
        });
    }
  }

  private changePerson() {
    this.person.valueChanges
      .subscribe((person) => {
        if (person && typeof person === 'object') {
          this.firstName.setValue(person.firstName);
          this.lastName.setValue(person.firstLastName);
          this.email.setValue(person.email1);
          this.generateUsername(person.firstName, person.firstLastName);
        }
      });
  }

  private searchPerson(id: string) {
    this.personService.searchByUserId(id)
      .subscribe((res: ShortPerson) => {
        this.person.setValue(res);
      });
  }
}
