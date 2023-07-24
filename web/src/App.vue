<template>
  <div>
   <then-header></then-header>
   <router-view></router-view>
  <then-footer></then-footer>
  </div>
</template>

<script>
import ThenHeader from "./components/then-header";
import ThenFooter from "./components/then-footer";
export default {
  name: 'App',
  components: {
    ThenFooter,
    ThenHeader
  }
}
</script>

