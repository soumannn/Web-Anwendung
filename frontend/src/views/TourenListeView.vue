<template>
    <h1 class="title">Das aktuelle Mitfahrangebot</h1>

  <!-- Suchfeld und Reset-Button -->
  <div>
    <input type="text" v-model="searchField" placeholder="Suche nach Zielort oder Startort " />
    <button @click="reset">Reset</button>
  </div>
  <!-- <button @click="updateTouren">Daten ändern</button>-->
  
  <!-- Bindet die tourlisteitems an die TourenListe-Komponente -->
  <TourenListe :touren="tourlistitems"></TourenListe> 

  </template>
  
  <script setup lang="ts">
  /*
   * TourenListe-Komponente bitte in src/components/tour selbst implementieren
   */
import { computed, onMounted, ref } from 'vue';
import { useTourenStore } from '@/stores/tourenstore';
import TourenListe from '@/components/tour/TourenListe.vue';
  
  // Initialisiert den Store
  const store = useTourenStore();

  // Reaktive Referenz für das Suchfeld
  const searchField = ref("");

  const {tourdata, updateTourListe}= store;

  // Reaktive Ref zur Tourliste vom Store
  const tourliste = computed(() =>tourdata.tourliste) ; //unveränderte, komplette Liste der Touren aus dem Store.

  // Lädt die Tourdaten beim Laden 
  onMounted(() => {
    updateTourListe();
  });

  //zur Filterung der Tourliste basierend auf dem Suchstring
  const tourlistitems = computed(() => { // Eigenschaft (computed), die die Liste tourdata.tourliste filtert.
   if(!searchField.value){
    return store.tourdata.tourliste;
   }
   else{
   const search = searchField.value.toLowerCase();
    return store.tourdata.tourliste.filter(tour =>      //basierend auf diesem Wert gefiltert
    tour.startOrtName.toLowerCase().includes(search) || //Touren, deren Start- oder Zielortnamen den Suchstring enthalten
    tour.zielOrtName.toLowerCase().includes(search)
    );
   }
  });
  
  // Funktion zur Zurücksetzung des Suchfelds
function reset() {
  searchField.value = '';
}
  /*
   * Provisorische Beispieldaten in "tourliste"-Ref bereitstellen,
   * kommt künftig über einen REST-Endpunkt von unserem Backend

  function updateTouren() {
  // Beispielhafte Änderungen an den Daten
  tourliste.value[0].preis += 5; // Preis der ersten Tour erhöhen
  tourliste.value[0].buchungen += 1; // Anzahl der Buchungen der ersten Tour erhöhen
}
*/
  </script>
  
  <style scoped></style>
  