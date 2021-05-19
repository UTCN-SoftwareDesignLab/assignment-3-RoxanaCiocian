<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create patient" : "Edit patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.firstName" label="First name" />
          <v-text-field v-model="patient.lastName" label="Last name" />
          <v-text-field v-model="patient.idNumber" label="ID nr" />
          <v-text-field v-model="patient.cnp" label="CNP" />
          <v-text-field v-model="patient.birthDate" label="Birthdate" />
          <v-text-field v-model="patient.address" label="Address" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="checkPatient">Check Patient</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PatientsDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.patient
          .createPatient({
            firstName: this.patient.firstName,
            lastName: this.patient.lastName,
            idNumber: this.patient.idNumber,
            cnp: this.patient.cnp,
            birthDate: this.patient.birthDate,
            address: this.patient.address,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.patient
          .editPatient(this.patient.id, {
            id: this.patient.id,
            firstName: this.patient.firstName,
            lastName: this.patient.lastName,
            idNumber: this.patient.idNumber,
            cnp: this.patient.cnp,
            birthDate: this.patient.birthDate,
            address: this.patient.address,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    checkPatient() {
      api.consultations.patientArrived();
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },
  },
};
</script>

<style scoped></style>
