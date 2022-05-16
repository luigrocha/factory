export interface TableHeader<T> {
  label: string;
  property: keyof T | string;
  cssClasses?: string[];
  frozen?: boolean;
}
