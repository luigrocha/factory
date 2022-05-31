export interface TableHeader<T> {
  label: string;
  property: keyof T | string;
  cssClasses?: string[];
  frozen?: boolean;
}

export interface TableColumn<T> {
  field: keyof T | string;
  header: string;
}
