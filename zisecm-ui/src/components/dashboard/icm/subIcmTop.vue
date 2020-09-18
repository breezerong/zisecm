<template>
 <div>
    <el-form inline="true">
      <el-row>
        <el-col :span="3">
          <el-form-item>
            <ecm-data-icons ref="p1" :option="projectData1"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item>
            <ecm-data-icons ref="p2" :option="projectData2"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item>
            <ecm-data-icons ref="p3" :option="projectData3"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item>
            <ecm-data-icons ref="p4" :option="projectData4"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col  :span="3">
          <el-form-item>
            <ecm-data-icons ref="p5" :option="projectData5"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item>
            <ecm-data-icons ref="p6" :option="projectData6"></ecm-data-icons>
          </el-form-item>
    </el-col>
    </el-row>
    </el-form>
 </div>
</template>


<script type="text/javascript">
import ecmDataIcons from "@/components/ecm-data-icons/ecm-data-icons";
export default {
  name: "planTopDashBoard",
  data() {
    return {
      projectData1: {
        data: [
          {
            title: "项目",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-warning",
            url: "",
          },
        ],
      },
      projectData2: {
        data: [
          {
            title: "计划",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document",
            url: "",
          },
        ],
      },
      projectData3: {
        data: [
          {
            title: "计划作业",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/DCManagement/receivingdc",
          },
        ],
      },
      projectData4: {
        data: [
          {
            title: "已生效IED",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/iedmanagement/IEDpublished",
          },
        ],
      },
      projectData5: {
        data: [
          {
            title: "ICM",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-delete",
            url: "",
          },
        ],
      },
      projectData6: {
        data: [
          {
            title: "反馈ICM",
            count: 0,
            color: "rgb(255, 0, 0)",
            icon: "el-icon-document-delete",
            url: "",
          },
        ],
      },
      a: [],
    };
  },
  mounted() {
    this.loadStatistic();
  },

  methods: {
    loadStatistic() {
      let _self = this;
      let mp = new Map();
      axios;
      axios
        .post("/dc/getSubIcm", JSON.stringify(mp))
        .then(function (response) {
          _self.projectData1.data[0].count = response.data.projectNum;
          _self.projectData2.data[0].count = response.data.planNum;
          _self.projectData3.data[0].count = response.data.thereplanNum;
          _self.projectData4.data[0].count = response.data.iedNum;
          _self.projectData5.data[0].count = response.data.icmNum;
          _self.projectData6.data[0].count = response.data.feedbackicmNum;
          _self.$refs.p1.refresh()
          _self.$refs.p2.refresh()
          _self.$refs.p3.refresh()
          _self.$refs.p4.refresh()
          _self.$refs.p5.refresh()
          _self.$refs.p6.refresh()
          console.log(response.data)
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },

  components: {
    ecmDataIcons: ecmDataIcons,
  },
};
</script>