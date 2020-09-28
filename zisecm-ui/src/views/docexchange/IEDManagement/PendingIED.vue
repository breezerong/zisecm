<template>
  <DataLayout>
    <el-dialog
      title="批量导入IED"
      :visible.sync="batchDialogVisible"
      width="80%"
    >
      <BatchImport
        ref="BatchImport"
        @onImported="onBatchImported"
        v-bind:deliveryId="parentId"
        width="100%"
      ></BatchImport>
      <div slot="footer" class="dialog-footer">
        <el-button @click="batchDialogVisible = false" size="medium">{{
          $t("application.close")
        }}</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="$t('application.Import')"
      :visible.sync="importdialogVisible"
      width="70%"
    >
      <el-form size="mini" :label-width="formLabelWidth" v-loading="uploading">
        <div style="height: 200px; overflow-y: scroll; overflow-x: scroll">
          <el-upload
            :limit="100"
            :file-list="fileList"
            action
            :on-change="handleChange"
            :auto-upload="false"
            :multiple="true"
          >
            <el-button slot="trigger" size="small" type="primary">{{
              $t("application.selectFile")
            }}</el-button>
          </el-upload>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="importdialogVisible = false">{{
          $t("application.cancel")
        }}</el-button>
        <el-button type="primary" @click="uploadData()">{{
          $t("application.start") + $t("application.Import")
        }}</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="$t('application.iedContrast')"
      :visible.sync="IEDcontrast"
      @close="IEDcontrast = false"
      width="80%"
    >
      <el-table :data="ContrastData" ref="IEDCon" row-key="id">
        <el-table-column
          fixed
          prop="C_IN_CODING"
          :label="$t('application.IN_CODING')"
          width="150"
        ></el-table-column>
        <el-table-column
          fixed
          prop="CODING"
          :label="$t('application.CODING')"
          width="150"
        ></el-table-column>
        <el-table-column
          fixed
          prop="TITLE"
          :label="$t('application.TITLE')"
          width="150"
        ></el-table-column>
        <el-table-column
          v-for="item in IEDcolumns"
          v-bind="item"
          :key="item"
          :label="item.label"
          :prop="item.attrName"
        ></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="IEDcontrast = false">{{
          $t("application.cancel")
        }}</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="驳回备注"
      :visible.sync="showDialog"
      width="80%"
      @close="showDialog = false"
    >
      <el-input
        type="textarea"
        :rows="5"
        placeholder="请输入内容"
        v-model="rejectComment"
      >
      </el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="rejectByCnpe()">{{
          $t("application.ok")
        }}</el-button>
      </div>
    </el-dialog>
    <template v-slot:header>
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <DataSelect
            v-model="value"
            dataUrl="/exchange/project/myproject"
            dataValueField="name"
            dataTextField="name"
            includeAll
            @onLoadnDataSuccess="onLoadnDataSuccess"
          ></DataSelect>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="input"
            :placeholder="$t('message.iedPublishedInputPlaceholder')"
            style="width: 200px"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search()">{{
            $t("application.SearchData")
          }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="submit()">{{
            $t("application.Receive")
          }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            type="success"
            @click="beforImport($refs.mainDataGrid, false, '')"
            >{{ $t("application.IEDOperExcel") }}</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click.native="exportData">{{
            $t("application.ExportExcel")
          }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" @click="clickShowDialog">驳回</el-button>
        </el-form-item>
      </el-form>
    </template>

    <template v-slot:main="{ layout }">
      <el-row>
        <el-col :span="24">
          <DataGrid
            ref="mainDataGrid"
            dataUrl="/dc/getDocuments"
            isshowOption
            v-bind="tables.main"
            :tableHeight="layout.height - 170"
            :optionWidth="2.5"
            gridViewName="IEDGrid"
            @cellMouseEnter="cellMouseEnter"
            @cellMouseleave="cellMouseleave"
            @rowclick="rowClick"
            @selectchange="selectChange"
          >
            <template slot="sequee" slot-scope="scope">
              <el-popover trigger="hover" placement="top" width="50">
                <div slot="reference">
                  <span
                    :style="
                      scope.data.row['C_ITEM_STATUS2'] == '变更中'
                        ? { background: '	#00FF00' }
                        : ''
                    "
                    >{{ scope.data.$index + 1 }}</span
                  >
                </div>
                <span>{{ scope.data.row.C_ITEM_STATUS2 }}</span>
              </el-popover>
            </template>
            <template slot="customMoreOption" slot-scope="scope">
              <el-button
                type="primary"
                @click="goContrast(scope.data.row)"
                size="mini"
                >{{ $t("application.contrast") }}</el-button
              >
            </template>
          </DataGrid>
        </el-col>
      </el-row>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import RejectButton from "@/components/RejectButton";
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from "@/utils/excel.js";
import DataSelect from "@/components/ecm-data-select";
import DataLayout from "@/components/ecm-data-layout";
import BatchImport from "@/components/controls/ImportIED";
export default {
  name: "PendingIED",
  data() {
    return {
      gridObj: "",

      tables: {
        main: {
          gridName: "IEDGrid",
          dataList: [],
          height: "",
          isInitData: false,
          isshowoption: true,
          isshowCustom: false,
          isShowPropertyButton: true,
          isShowMoreOption: false,
          isShowChangeList: false,
          isshowicon: false,
        },
        loading: false,
        status: "已完成",
      },
      Subcontractors: [
        {
          name: "",
        },
      ],
      IEDcolumns: [],
      ContrastData: [
        {
          C_IN_CODING: "",
          CODING: "",
          TITLE: "",
        },
      ],
      value: "所有项目",
      Subcontractor: "",
      input: "",
      batchDialogVisible: false,
      rejectComment: "",
      showDialog: false,
      contractors: [],
      id: "",
      IEDcontrast: false,
      selectedItems: [],
      selectedItemId: "",
    };
  },
  created() {},
  mounted() {
    if (!this.validataPermission()) {
      //跳转至权限提醒页
      let _self = this;
      _self.$nextTick(() => {
        _self.$router.push({ path: "/NoPermission" });
      });
      console.log(sessionStorage.data.data.groupname);
    }
    this.getSubContractors();
  },
  methods: {
    beforImport(obj, isSub, relationName) {
      this.gridObj = obj;
      this.batchDialogVisible = true;
      this.$nextTick(() => {
        if (isSub) {
          this.$refs.BatchImport.deliveryId = this.parentId;
          this.$refs.BatchImport.relationName = relationName;
        } else {
          this.$refs.BatchImport.deliveryId = "";
          this.$refs.BatchImport.relationName = "";
        }
      });
    },

    goContrast(row) {
      let _self = this;
      this.IEDcontrast = true;
      var ver_id;
      ver_id = row.VERSION_ID;
      this.id = row.ID;
      var m = new Map();
      m.set("ID", this.id);
      m.set("Version_id", ver_id);
      let formdata = new FormData();
      m.set("lang", _self.getLang());
      formdata.append("metaData", JSON.stringify(m));
      axios
        .post("/exchange/ied/iedContrast", formdata, {
          "Content-Type": "multipart/form-data",
        })
        .then(function (response) {
          console.log(response.data);
          _self.ContrastData = response.data;
          _self.getColumn();
        });
    },
    getColumn() {
      var _self = this;
      axios.post("/exchange/ied/getColumn").then(function (response) {
        console.log(response.data);
        _self.IEDcolumns = response.data;
      });
    },

    fresh() {
      let _self = this;
      _self.$refs.mainDataGrid.loadGridData();
    },
    getSubContractors() {
      let _self = this;
      let pm = new Map();
      pm.set("configName", "GetSubContractor");
      // pm.set('parentId',"'"+p+"'");
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
          method: "post",
          data: JSON.stringify(pm),
          url: "/dc/getObjectsByConfigClauseNoPage",
        })
        .then(function (response) {
          var i;
          console.log(response.data.data);
          _self.Subcontractors = response.data.data;
          for (i = 0; i < _self.Subcontractors.length; i++) {
            _self.contractors[i] = _self.Subcontractors[i].NAME;
          }
          console.log(_self.contractors);
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    cellMouseEnter(row, column, cell, event) {
      this.selectRow = row;
    },
    onLoadnDataSuccess(select, options) {
      console.log(select);
      this.search();
      this.getSubContractors();
    },

    rowClick(row) {
      this.selectRow = row;
      console.log(row);
    },
    selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
      console.log(this.selectedItems);
    },
    submit() {
      if (this.selectedItems.length == 0) {
        let msg = this.$t("message.pleaseSelectIED");
        this.$message({
          showClose: true,
          message: msg,
          duration: 2000,
          type: "warning",
        });
        return;
      }
      this.onNextStatus(this.selectedItems, this.$refs.mainDataGrid);
      this.search();
    },
    search() {
      let _self = this;
      var k1 = "TYPE_NAME='IED' AND STATUS='审核中'";

      let wheres = ["TITLE", "C_IN_CODING", "CODING"];
      let orS = "";
      if (_self.input.trim().length > 0) {
        wheres.forEach(function (item) {
          if (orS.length > 0) {
            orS += " OR ";
          }
          orS += item + " LIKE '%" + _self.input + "%'";
        });
        k1 += " AND (" + orS + ")";
      }
      let user = this.currentUser();
      if (_self.value != undefined && _self.value != "所有项目") {
        k1 += " AND C_PROJECT_NAME in (" + _self.value + ")";
      }
      console.log(k1);
      _self.$refs.mainDataGrid.condition = k1;
      _self.fresh();
      _self.getSubContractors();
    },
    exportData() {
      let _self = this;
      let dataUrl = "/exchange/doc/export";
      var fileDate = new Date();
      let fileDateStr =
        fileDate.getFullYear() +
        "" +
        fileDate.getMonth() +
        "" +
        fileDate.getDate();
      let params = {
        gridName: "IEDGrid",
        lang: "zh-cn",
        condition: _self.$refs.mainDataGrid.condition,
        filename: "IED_Pending_" + fileDateStr + ".xlsx",
        sheetname: "Result",
      };
      ExcelUtil.export(params);
    },

    clickShowDialog() {
      let _self = this;
      _self.showDialog = true;
    },

    rejectByCnpe() {
      let _self = this;
      var m = [];
      let tab = _self.selectedItems;
      console.log(_self.selectedItems);
      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      let mp = new Map();
      mp.set("ids", m);
      mp.set("rejectCommon", _self.rejectComment);
      axios
        .post("/dc/previousStatus", JSON.stringify(mp), {
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
          },
        })
        .then(function (response) {
          if (response.data.code == 1) {
            _self.selectedItems = "";
            _self.$refs.mainDataGrid.loadGridData();

            _self.$message({
              showClose: true,
              message: _self.$t("message.rollbackSuccess"),
              duration: 2000,
              type: "success",
            });
          } else {
            _self.$message({
              showClose: true,
              message: _self.$t("message.operationFaild"),
              duration: 5000,
              type: "error",
            });
          }
        })
        .catch(function (error) {
          _self.$message({
            showClose: true,
            message: _self.$t("message.operationFaild"),
            duration: 5000,
            type: "error",
          });
          console.log(error);
        });
      _self.showDialog = false;
    },
  },
  props: {},
  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataSelect: DataSelect,
    RejectButton: RejectButton,
    BatchImport: BatchImport,
    DataLayout: DataLayout,
  },
};
</script>
<style scoped>
.el-header {
  height: auto;
}
.el-form-item {
  margin: 0px;
}
</style>