export interface Machine {
  id: number;
  name: string;
  hasDesb: boolean;
}

export interface CreateMachine extends Omit<Machine, 'id'> {
}


export interface UpdateMachine extends Omit<Machine, 'id'> {
}
