export type EventType = 'TOUR';
export type OperationType = 'CREATE' | 'UPDATE' | 'DELETE';

export interface IFrontendNachrichtEvent {
  eventType: EventType;
  id: number;
  operation: OperationType;
}