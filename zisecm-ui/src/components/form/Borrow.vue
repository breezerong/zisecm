     <template>
     <div>
          <el-form :model="borrowForm" style="width:100%">
            <el-row style="width:100%">
              <div v-if="1==1">
              <el-col >
                <el-form-item label="姓名" :label-width="formLabelWidth"  style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="部门" :label-width="formLabelWidth"  style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="电话" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="日期" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="文件编制部门" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="档案类型" :label-width="formLabelWidth" style="float:left">
                  <el-radio-group  v-model="borrowForm.result"  >
                      <el-radio-button label="在线浏览" aria-checked="true">在线浏览</el-radio-button>
                      <el-radio-button label="纸质借阅" >纸质借阅</el-radio-button>
                      <el-radio-button label="下载">下载</el-radio-button>
                   </el-radio-group>
                </el-form-item>
               </el-col>
             <el-col>
                 <el-form-item label="借阅开始时间" :label-width="formLabelWidth" style="float:left">
                  <el-date-picker   v-model="borrowForm.message" auto-complete="off"></el-date-picker >
                </el-form-item>
                 <el-form-item label="借阅结束时间" :label-width="formLabelWidth" style="float:left">
                  <el-date-picker    v-model="borrowForm.message" auto-complete="off"></el-date-picker >
                </el-form-item>
               </el-col>
             <el-col>
            <el-table
              :data="dataList"
              border
              v-loading="loading"
              @selection-change="selectChange"  >
                <el-table-column type="selection" width="40">
                </el-table-column> 
                <el-table-column type="index" label="#" width="50">
                </el-table-column>
                <el-table-column prop="name" label="文号" min-width="15%" sortable>
                </el-table-column>
                <el-table-column prop="startUser" label="提名" min-width="15%" sortable>
                </el-table-column>
                <el-table-column prop="createTime" label="密级" :formatter="dateFormatter" min-width="10%" sortable>
                </el-table-column>
                <el-table-column label="操作"  width="80">
                  <template slot-scope="scope">
                    <el-button :plain="true" type="success" size="small" icon="save" @click="showitem(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
        </el-table>
            </el-col>
            <el-col style="padding:3px">
             <el-button type="primary" @click="alert();" size="medium" style="float:left">继续添加</el-button>
              <el-button type="primary" @click="alert();" size="medium" style="float:left">移除所选</el-button>
           </el-col>
            <el-col>
                <el-form-item label="申请人领导" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="形成部门领导" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="分管领导" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>

              </el-col>
               <el-col>
                <el-form-item label="借阅目的" :label-width="formLabelWidth">
                  <el-input type="textarea" :autosize="{minRows:3}" v-model="borrowForm.message" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
             </div>

            </el-row>
          </el-form>


          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="completetask(form)">完成任务</el-button>
          </div>
        </div>
</template>


<script type="text/javascript">
 export default {
  name: "ShowProperty",
  data() {
    return {  
       dataList: [],
       loading: false,
          formLabelWidth: "100px",
      borrowData: [],
      dialogTitle:"借阅",
       borrowDialogVisible: false,
          borrowForm: {
            taskId: 0,
            result: "在线浏览",
            message: ""

        }
    };
  },
  methods: {
       selectChange(selection) {
          this.selectedItemList = [];
          if (selection.length > 0) {
            for (var i = 0; i < selection.length; i++) {
              this.selectedItemList.push(selection[i]);
            }
          }
       },
           dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },

  }
};

</script>