import { UsersService } from 'src/app/core/http/users/users.service';
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export class EmailValidator {
  static createValidator(userService: UsersService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors> => {
      return userService
        .existsByEmail(control.value)
        .pipe(
          map((result: boolean) =>
            result ? { emailAlreadyExists: true } : null
          )
        );
    };
  }
}
