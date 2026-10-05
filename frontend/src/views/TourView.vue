<template>
  <div>
    <h1>Tour Details</h1>
    <div v-if="tour">
      <p><strong>ID:</strong> {{ tour.id }}</p>
      <p><strong>Abfahrzeit:</strong> {{ tour.abfahrDateTime }}</p>
      <p><strong>Preis:</strong> {{ tour.preis }} €</p>
      <p><strong>Plätze:</strong> {{ tour.plaetze }}</p>
      <p><strong>Buchungen:</strong> {{ tour.buchungen }}</p>
      <p><strong>Startort:</strong> {{ tour.startOrtName }}</p>
      <p><strong>Zielort:</strong> {{ tour.zielOrtName }}</p>
      <p><strong>Anbieter:</strong> {{ tour.anbieterName }}</p>
      <p><strong>Distanz:</strong> {{ tour.distanz }} km</p>
      <p><strong>Info:</strong> {{ tour.info }}</p>
    </div>
    <div v-else>
      <p>Tour nicht gefunden</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { computed, onMounted  } from 'vue';
import { useTourenStore } from '@/stores/tourenstore';
import type { ITourDTD } from '@/stores/ITourDTD';

import { useInfo } from '@/composables/useInfo';

const route = useRoute();
const tourenStore = useTourenStore();

const tourId = route.params.id as string;

const tour = computed<ITourDTD | undefined>(() => {
  return tourenStore.tourdata.tourliste.find(t => t.id === parseInt(tourId));
});
const { setzeInfo,loescheInfo } = useInfo();

onMounted( async() => {
  if (!tourenStore.tourdata.ok) {
    await tourenStore.updateTourListe();
  }
  if (tour.value && tour.value.distanz > 300) {
    setzeInfo(`Warnung: Die Tour von ${tour.value.startOrtName} nach ${tour.value.zielOrtName} ist über 300 km lang.`);
  }else{
    loescheInfo();
  }
});

</script>

<style scoped>
/* Stile für die TourView */
</style>
