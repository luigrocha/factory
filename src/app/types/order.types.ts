import { Priority, Status } from 'src/app/types/catalogs.types';
import { Client } from './client.types';

export interface Order {
  id: number;
  code: string;
  productCode: string;
  name: string;
  quantity: number;
  orderedAt: Date;
  lot: string;
  estimatedDeliveryAt: Date;
  clientOrderCode: string;
  observation: string;
  completedAt: Date;
  canceledAt: Date;
  productionStartedAt: Date;
  cancellationReason: string;
  lastModifiedAt: Date;
  client: Client;
  status: Status;
  pendingQuantity: number;
  shippedQuantity: number;
  priority: Priority;
}
