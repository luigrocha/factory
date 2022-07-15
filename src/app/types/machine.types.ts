export interface Machine {
  id: number;
  name: string;
  description: string;
  observation: string;
  widthExt?: number;
  hasDesb?: boolean;
  abilityPell?: number;
  machineCatalog: MachineCatalog;
}

export interface MachineCatalog{
  id: number;
  name: string;
}

export interface CreateMachine extends Omit<Machine, 'id'> {
}


export interface UpdateMachine extends Omit<Machine, 'id'> {
}
