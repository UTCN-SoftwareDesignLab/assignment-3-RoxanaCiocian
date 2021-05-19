import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import Patients from "../views/Patients.vue";
import ConsultationList from "../views/ConsultationList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import DescriptionList from "../views/DescriptionList";
//import PatientsDialog from "../components/PatientsDialog";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Books" });
      }
    },
  },
  {
    path: "/patients",
    name: "Patients",
    component: Patients,
    beforeEnter: (to, from, next) => {
      if (store.getters.isSecretary) {
        next();
      } else {
        next({ name: "Books" });
      }
    },
  },
  {
    path: "/consultations",
    name: "Consultations",
    component: ConsultationList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Booksttore" });
      }
    },
  },
  {
    path: "/consultations/doctor",
    name: "Consultations",
    component: DescriptionList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isDoctor) {
        next();
      } else {
        next({ name: "Booksttore" });
      }
    },
  },

  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
