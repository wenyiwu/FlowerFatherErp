<template>
  <div class="app-container">
    <el-input v-model="search" placeholder="输入名称关键字搜索" size="mini" align="right"/>
    <el-table
      v-loading="Loading"
      :data="data.filter(data => !search || data.Name.toLowerCase().includes(search.toLowerCase()))"
      :default-sort = "{prop: 'CreatedAt', order: 'descending'}"
      element-loading-text="Loading"
      style="width: 100%"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item label="外部订单号">
              <span>{{ props.row.externalOrderId }}</span>
            </el-form-item>
            <el-form-item label="客户姓名">
              <span>{{ props.row.customerName }}</span>
            </el-form-item>
            <el-form-item label="预计送货时间">
              <span>{{ props.row.deliveryAt }}</span>
            </el-form-item>
            <el-form-item label="预计送货地址">
              <span>{{ props.row.deliveryAddress }}</span>
            </el-form-item>
            <el-form-item label="运费">
              <span>{{ props.row.freight }}</span>
            </el-form-item>
            <el-form-item label="备注">
              <span>{{ props.row.remarks }}</span>
            </el-form-item>
          </el-form>

          <el-table :data="props.row.goods" style="width: 100%">
            <el-table-column label="ID" prop="goodId"/>
            <el-table-column label="名称" prop="name"/>
            <el-table-column label="颜色" prop="colour"/>
            <el-table-column label="分类" prop="classify"/>
            <el-table-column label="等级" prop="level"/>
            <el-table-column label="数量" prop="number"/>
            <el-table-column label="单价" prop="price"/>
          </el-table>
        </template>

      </el-table-column>
      <el-table-column
        :filters="[{ text: '未完成', value: '未完成' }, { text: '已完成', value: '已完成' }]"
        :filter-method="filterTag"
        label="状态"
        prop="state"/>
      <el-table-column label="订单ID" prop="id"/>
      <el-table-column label="订单类型" prop="orderType"/>
      <el-table-column label="客户来源" prop="customerSource"/>
      <el-table-column label="花艺师" prop="florist"/>
      <el-table-column label="订单总额" prop="amount"/>
      <el-table-column label="客户电话" prop="customerTel"/>
      <el-table-column label="时间" prop="orderCreatedAt" sortable/>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.state === '未完成'" size="mini" type="primary" icon="el-icon-check" circle @click="handleConfirmOrder(scope.row)"/>
          <el-button v-if="scope.row.state === '未完成'" size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"/>
          <el-button size="mini" type="success" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"/>
          <!--<el-button size="mini" type="success" icon="el-icon-download" circle @click="handleDownload(scope.row)"/>-->
        </template>
      </el-table-column>
    </el-table>
    <!-- 跟新表单 -->
    <el-dialog :visible.sync="dialogFormVisible" title="修改用户订单信息" width="99%">
      <el-form v-enterToNext="true" ref="postForm" :model="postForm" :rules="rules" status-icon label-width="50px" >
        <el-form-item label="订单ID" prop="id">
          <span>{{ postForm.id }}</span>
        </el-form-item>

        <el-form-item label="花艺师" prop="florist">
          <el-input v-model="postForm.florist"/>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
  .table-expand {
    font-size: 0;
  }
  .table-expand label {
    width: 130px;
    color: #99a9bf;
  }
  .table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
</style>

<script>
import { getCustormerOrders, deleteCustormerOrder, confirmCustormerOrder, updateCustomerOrder } from '@/api/order'
import { parseTime } from '@/utils'

export default {
  data() {
    return {
      Loading: true,
      data: [],
      search: '',
      dialogFormVisible: false,
      postForm: {
        id: '',
        florist: ''
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.Loading = true
      getCustormerOrders().then(response => {
        console.log(response)
        const len = response.length
        for (let i = 0; i < len; i++) {
          response[i].CreatedAt = this.formatTime(response[i].CreatedAt)
        }
        this.data = response
        this.Loading = false
      })
    },
    filterTag(value, row) {
      return row.State === value
    },
    handleConfirmOrder(data) {
      this.$prompt('此操作将确认订单, 请输入运费:', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^\d+(\.\d+)?$/,
        inputErrorMessage: '请输入运费'
      })
        .then(({ value }) => {
          this.Loading = true
          data.freight = Number(value)
          confirmCustormerOrder(data)
            .then(response => {
              this.Loading = false
              this.$message.success('确认成功')
              this.fetchData()
            })
            .catch((error) => {
              this.Loading = false
              this.$message.error('确认失败: ' + error.response.data.error)
            })
        })
        .catch(() => {
          this.$message.info('已取消确认')
        })
    },
    handleDelete(data) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.Loading = true
          deleteCustormerOrder(data)
            .then(response => {
              this.Loading = false
              this.$message.success('删除成功')
              this.fetchData()
            })
            .catch(() => {
              this.Loading = false
              this.$message.error('删除失败')
            })
        })
        .catch(() => {
          this.$message.info('已取消删除')
        })
    },
    handleDownload(row) {
      this.Loading = true

       import('@/vendor/Export2Excel').then(excel => {
         const tHeader = ['客户姓名', '电话', '送货地点', '送货时间', '产品编号', '名称', '颜色', '尺寸', '品牌', '数量']
         const filterVal = ['CustomerName', 'Tel', 'DeliveryAddress', 'DeliveryTime', 'ID', 'Name', 'Colour', 'Size', 'Brand', 'Number']
         const data = this.formatJson(filterVal, row.Goods)

         const len = filterVal.length
         const orderInfo = new Array(len)
         for (let i = 0; i < len; i++) {
           orderInfo[i] = undefined
         }
         orderInfo[0] = row.Name
         orderInfo[1] = row.Tel
         orderInfo[2] = row.DeliveryAddress
         orderInfo[3] = row.DeliveryTime
         data.unshift(orderInfo)

         const fileName = '客户订单-' + row.Name + '-' + row.Tel + '-' + row.DeliveryAddress
         excel.export_json_to_excel({
           header: tHeader,
           data,
           autoWidth: true,
           filename: fileName
         })
       })
       this.Loading = false
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        return v[j]
      }))
    },
    formatTime(time) {
      return parseTime(time)
    },
    handleEdit(index, data) {
      this.dialogFormVisible = true
      this.postForm.id = data.id
      this.postForm.florist = data.florist
    },
    handleEditConfirm() {
      this.Loading = true
      updateCustomerOrder(this.postForm)
        .then(response => {
          console.log(response)
          this.$message.success('更新成功')
          this.Loading = false
          this.dialogFormVisible = false
          this.fetchData()
        })
        .catch(() => {
          this.Loading = false
          this.$message.error('更新失败')
        })
    }
  }
}
</script>
