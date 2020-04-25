<template>
  <div>
    <el-tooltip effect="dark" content="帮助" placement="bottom">
      <el-button class=" btn-text can-hover" type="text" @click="dialogVisible = true">
        <i class="el-icon-question"></i>
      </el-button>
    </el-tooltip>
    <el-dialog title="帮助" width="700px" :visible.sync="dialogVisible" append-to-body>
      <div style="margin-top: -25px;">
        <h1 >如果你有问题可以联系管理员</h1>
        <el-row :gutter="20">
          <el-col :span="12">
            
          </el-col>
          <el-col :span="12">
            
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ecmIcon from '@/components/ecm-icon'
export default {
  name: 'ecm-help',
  components: {
    ecmIcon
  },
  data() {
    return {
      dialogVisible: false
    }
  }
}
</script>

<style lang="scss" scoped>
img {
  &.qr {
    width: 100%;
  }
}
 .btn-text {
        color: #5a5e66;
        &.can-hover {
          &:hover {
            color: #FFEBA4;
            background-color:#5a5e66;
          }
        }
      }

</style>
