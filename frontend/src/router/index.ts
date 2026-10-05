import { createRouter, createWebHistory } from 'vue-router';
import TourenListeView from '@/views/TourenListeView.vue';
import TourView from '@/views/TourView.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/touren',
    },
    {
      path: '/touren',
      component: TourenListeView,
    },
    {
      path: '/tour/:id',
      component: TourView,
    },
  ],
})

export default router
