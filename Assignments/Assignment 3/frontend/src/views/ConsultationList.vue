<template>
  <v-card>
    <v-card-title>
      Secretary - Consultations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addConsultation">Add Consultation</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="editConsultation"
    ></v-data-table>
    <ConsultationDialog
      :opened="dialogVisible"
      :consultation="selectedUser"
      @refresh="refreshList"
    >
    </ConsultationDialog>
    <v-btn @click="goToPatients">Go to Patients</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import ConsultationDialog from "../components/ConsultationDialog";
import router from "../router";

export default {
  name: "ConsultationList",
  components: { ConsultationDialog },
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
    editConsultation(consul) {
      this.selectedUser = consul;
      this.dialogVisible = true;
    },
    addConsultation() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.consultations = await api.consultations.allConsultations();
    },

    goToPatients() {
      router.push("/patients");
    },
  },

  created() {
    this.refreshList();
  },
};
</script>
