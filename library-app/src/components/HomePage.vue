<template>
  <div class="small-container">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide
        img-src="https://atrium-plejada.pl/assets/uploads/2019/04/Plejada_Biblioteka_Multi_2019_Slider_1920x595pix.jpg"
      ></b-carousel-slide>
      <b-carousel-slide
        img-src="https://biblioteka.gorakalwaria.pl/wp-content/uploads/2023/03/slider-drzewko-za-makulature-1400x440.png"
      ></b-carousel-slide>

      <b-carousel-slide>
        <template #img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            src="https://biblioteka.gorakalwaria.pl/wp-content/uploads/2022/09/309780123_464015645496759_4255085769658170092_n-1400x440.jpg"
            alt="image slot"
          />
        </template>
      </b-carousel-slide>
    </b-carousel>
    <div class="article">
      <h2>Nobel in literature</h2>
      <span
        >The 2022 Nobel Prize in Literature was awarded to the French author
        Annie Ernaux "for the courage and clinical acuity with which she
        uncovers the roots, estrangements and collective restraints of personal
        memory".</span
      >
      <h3>Laureate</h3>
      <span
        >Ernaux started her literary career in 1974 with Les Armoires vides
        ("Cleaned Out"), an autobiographical novel. Very early in her career,
        she turned away from fiction to focus on autobiography, combining
        historic and individual experiences. In her different viewpoints, she
        consistently examines a life marked by strong disparities regarding
        gender, language and class. Her path to authorship was long and arduous,
        and all her oeuvres are written in plain language. Her books are
        followed by a wide readership, and are reviewed in most local and
        national newspapers in France, as well as being the subject of many
        radio and television interviews and programmes, and a large and growing
        international academic literature. Her famous works include La Place ("A
        Man's Place", 1983), L'événement ("Happening", 2000), Se perdre
        ("Getting Lost", 2001), L'Occupation ("The Possession", 2002), and Les
        Années ("The Years", 2008)</span
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>
<style scoped>
.article {
  padding: 40px;
}
</style>