export interface FirstDigit {
  id: string;
  name: string;
}

export interface SecondDigit {
  id: string;
  name: string;
}

export interface Division {
  id: string;
  name: string;
}

export interface PersonGroup {
  id: string;
  name: string;
  ch: boolean;
  firstDigit: FirstDigit;
  secondDigit: SecondDigit;
  division: Division;
}
