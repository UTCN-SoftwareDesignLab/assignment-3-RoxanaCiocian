<template>
  <v-card>
    <v-card-title>
      Secretary: Patients <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addPatient">Add Patient</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="patient"
      :search="search"
      @click:row="editPatient"
    ></v-data-table>
    <PatientsDialog
      :opened="dialogVisible"
      :patient="selectedItem"
      @refresh="refreshList"
    ></PatientsDialog>
    <v-btn @click="goToConsultations">Go to Consultations</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import PatientsDialog from "../components/PatientsDialog";
import router from "../router";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "Patients",
  components: { PatientsDialog },
  data() {
    return {
      patient: [],
      search: "",
      headers: [
        {
          text: "First name",
          align: "start",
          sortable: false,
          value: "firstName",
        },
        { text: "Last name", value: "lastName" },
        { text: "ID Nr", value: "idNumber" },
        { text: "CNP", value: "cnp" },
        { text: "Birthday", value: "birthDate" },
        { text: "Address", value: "address" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editPatient(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addPatient() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.patient = await api.patient.allPatients();
    },

    goToConsultations() {
      router.push("/consultations");
    },
  },
  created() {
    this.refreshList();
    this.stompClient = Stomp.over(
      new SockJS("http://localhost:8088/gs-guide-websocket")
    );
    this.stompClient.connect(
      {},
      (frame) => {
        this.connected = true;
        console.log(frame);
        this.stompClient.subscribe("/doctor/patientArrived", (message) => {
          console.log(message);
        });
      },
      (error) => {
        console.log(error);
        this.connected = false;
      }
    );
    // this.stompClient.connect({}, function (connection) {
    //   console.log(connection);
    //   this.stompClient.subscribe("/doctor/notification", function (greeting) {
    //     console.log(JSON.parse(greeting.body).content);
    //   });
    // });
  },
};
</script>

<style scoped></style>
