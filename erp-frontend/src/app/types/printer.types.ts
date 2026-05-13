export interface Printer {
  id: number;
  name: string;
  numColors: number;
  description: string;
}

export interface CreatePrinter extends Omit<Printer, 'id'> {
}

export interface UpdatePrinter extends Omit<Printer, 'id'> {
}
