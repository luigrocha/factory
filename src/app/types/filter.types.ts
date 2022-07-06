export interface PFilterElement {
  value: any;
  matchMode: string;
  operator: string;
}

export interface PFilter {
  key: string;
  values: PFilterElement[];
}
