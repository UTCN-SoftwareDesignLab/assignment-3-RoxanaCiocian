<template>
  <v-card>
    <v-card-title>
      Doctor - Consultations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <DescriptionDialog
      :opened="dialogVisible"
      :consultation="selectedUser"
      @refresh="refreshList"
    >
    </DescriptionDialog>
  </v-card>
</template>

<script>
import api from "../api";
import DescriptionDialog from "../components/DescriptionDialog";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "DescriptionList",
  components: { DescriptionDialog },
  data() {
    return {
      consultations: [],
      search: "",
      headers: [
        {
          text: "Date",
          align: "start",
          sortable: false,
          value: "date",
        },
        { text: "Doctor Id", value: "doctorId" },
        { text: "Patient Id", value: "patientId" },
        { text: "Description", value: "description" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.consultations = await api.consultations.allConsultations();
    },
  },

  async created() {
    this.refreshList();
    this.stompClient = Stomp.over(
      new SockJS("http://localhost:8088/gs-guide-websocket")
    );
    this.stompClient.connect(
      {},
      (frame) => {
        this.connected = true;
        console.log(frame);
        this.stompClient.subscribe(
          "/doctor/newConsultNotification",
          (message) => {
            console.log(message);
          }
        );
      },
      (error) => {
        console.log(error);
        this.connected = false;
      }
    );
  },
};
</script>
