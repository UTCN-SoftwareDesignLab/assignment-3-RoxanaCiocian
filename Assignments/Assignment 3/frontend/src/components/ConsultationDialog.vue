<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create consultation" : "Edit consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="consultation.date" label="Date" />
          <v-text-field v-model="consultation.doctorId" label="Doctor id" />
          <v-text-field v-model="consultation.patientId" label="Patient id" />
          <v-text-field
            v-model="consultation.description"
            label="Description"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteConsultation">Delete Consultation</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "ConsultationDialog",
  props: {
    consultation: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.consultations
          .createConsultation({
            date: this.consultation.date,
            doctorId: this.consultation.doctorId,
            patientId: this.consultation.patientId,
            description: this.consultation.description,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.consultations
          .editConsultation(this.consultation.id, {
            date: this.consultation.date,
            doctorId: this.consultation.doctorId,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteConsultation() {
      api.consultations
        .deleteConsultation(this.consultation.id)
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.consultation.id;
    },
  },
};
</script>

<style scoped></style>
