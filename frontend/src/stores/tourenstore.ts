import { defineStore } from 'pinia';
import { reactive } from 'vue';
import type { ITourDTD } from './ITourDTD';
import { useInfo } from '@/composables/useInfo';
import { Client, type StompSubscription } from '@stomp/stompjs';
import type { IFrontendNachrichtEvent } from '@/services/IFrontendNachrichtEvent';

/**
 * implementiert einen Pinia-Store, der Tourdaten von einer API abzurufen und 
 * mittels STOMP-WebSocket-Clients in Echtzeit zu aktualisieren, dabei werden Fehler behandelt und Benachrichtigungen gesetzt.
 */

// WebSocket-URL und Ziel für STOMP-Nachrichten
const wsurl = `ws://localhost:8080/stompbroker`;
const DEST = "/topic/tour";

let stompClient: Client | null = null;
let stompSubscription: StompSubscription | null = null;

export const useTourenStore = defineStore('tourenstore', () => {

  const { setzeInfo } = useInfo();
  // Reaktive Daten für Tourdaten
  const tourdata = reactive({
    ok: false,
    tourliste: [] as ITourDTD[]
  });

  // Funktion zum Aktualisieren der Tourenliste
  async function updateTourListe() {
    try {
      const response = await fetch('/api/tour');
      if (!response.ok) {
        throw new Error('Server hat Fehler');
      }
      const touren: ITourDTD[] = await response.json();
      tourdata.tourliste = touren;
      tourdata.ok = true;

      // Starten des Live-Updates für Tourdaten
      startTourLiveUpdate();
    } catch (error) {
      console.error('Fetch error:', error);
      tourdata.tourliste = [];
      tourdata.ok = false;
      
      let errorMessage = 'Unbekannter Fehler';
      if (error instanceof Error) {
        errorMessage = error.message;
      }
      setzeInfo(`Fehler beim Abrufen der Tourenliste: ${errorMessage}`);
    }
  }

  // Funktion zum Starten des Live-Updates für Tourdaten
  function startTourLiveUpdate() {
    if (!stompClient) {
      stompClient = new Client({ brokerURL: wsurl });
      stompClient.onWebSocketError = (event) => {
        console.error('WebSocket-Fehler:', event);
        setzeInfo('WebSocket-Verbindungsfehler');
      };
      stompClient.onStompError = (frame) => {
        console.error('STOMP-Fehler:', frame);
        setzeInfo('STOMP-Fehler');
      };
      stompClient.onConnect = (frame) => {
        console.log('zum Server verbindet ', frame);
        setzeInfo('zum Server verbindet ');

        if (!stompSubscription) {
          stompSubscription = stompClient!.subscribe(DEST, (message) => {
            console.log('Nachricht von Server:', message.body);
            try {
              const frontendNachricht: IFrontendNachrichtEvent = JSON.parse(message.body);
              console.log('frontendNachricht:', frontendNachricht);
              updateTourListe();
            } catch (error) {
              console.error('Fehler der Nachricht:', error);

              setzeInfo('Fehler der Nachricht');
            }
          });
        }
      };
    }
    
    if (!stompClient.connected) {
      stompClient.activate();
    }
  }

  return { tourdata, updateTourListe, startTourLiveUpdate };
});



