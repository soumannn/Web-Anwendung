// src/composables/useInfo.ts
import { ref, readonly } from 'vue';

// Definieren der String-Ref info auf Modulebene
const info = ref('Dies ist ein Nachricht');

// Export der Composition Function useInfo
export function useInfo() {
  // Funktion zum Löschen der Info
  function loescheInfo() {
    info.value = '';
  }

  // Funktion zum Setzen der Info
  function setzeInfo(msg: string) {
    info.value = msg;
  }

  // Rückgabe eines Objekts mit den drei gewünschten Elementen
  return {
    info: readonly(info),
    loescheInfo,
    setzeInfo
  };
}
