import { Priority, Status } from 'src/app/types/catalogs.types';
import { Client } from './client.types';

export interface Order {
    id?: number;
    code?: string;
    lote?: string;
    amount?: number;
    deliverAt?: Date;
    order?: number;
    observation?: string;
    difference?: number;
    client?: Client;
    status?: Status;
    priority?: Priority;
}
