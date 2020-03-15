<template>
  <div class="app-container">
    <el-form v-enterToNext="true" ref="postForm" :model="postForm" :rules="rules" label-position="left" label-width="110px" size="mini" status-icon >
      <el-form-item label="订单类型" prop="orderType">
        <el-input v-model="postForm.orderType" />
      </el-form-item>
      <el-form-item label="客户来源" prop="customerSource">
        <el-input v-model="postForm.customerSource" />
      </el-form-item>
      <el-form-item label="外部订单号" prop="externalOrderId">
        <el-input v-model="postForm.externalOrderId" />
      </el-form-item>
      <el-form-item label="客户姓名" prop="customerName">
        <el-input v-model="postForm.customerName" />
      </el-form-item>
      <el-form-item label="客户电话" prop="customerTel">
        <el-input v-model="postForm.customerTel"/>
      </el-form-item>
      <el-form-item label="下单时间" prop="orderCreatedAt">
        <el-date-picker v-model="postForm.orderCreatedAt" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" tyle="width:185px ;"/>
      </el-form-item>
      <el-form-item label="预计送货时间" prop="deliveryAt">
        <el-date-picker v-model="postForm.deliveryAt" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" tyle="width:185px ;"/>
      </el-form-item>
      <el-form-item label="预计送货地址" prop="deliveryAddress">
        <el-input v-model="postForm.deliveryAddress"/>
      </el-form-item>
      <el-form-item label="花艺师" prop="florist">
        <el-input v-model="postForm.florist"/>
      </el-form-item>
      <el-form-item label="订单总额" prop="amount">
        <el-input v-model.number="postForm.amount" type="number" v-text="postForm.amount"/>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="postForm.remarks"/>
      </el-form-item>

    </el-form>

    <el-select v-model="selectValue" filterable clearable placeholder="请选择" size="mini" @change="selectChange">
      <el-option
        v-for="item in options"
        :key="item.id"
        :label="item.id + ',' + item.name + ',' + item.level"
        :value="item"
        :disabled="item.Disabled"/>
    </el-select>
    <el-table :data="commodities" style="width: 100%">
      <el-table-column label="ID" prop="id"/>
      <el-table-column label="名称" prop="name"/>
      <el-table-column label="颜色" prop="colour"/>
      <el-table-column label="分类" prop="classify"/>
      <el-table-column label="等级" prop="level"/>
      <el-table-column label="数量" width="150">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.number" :min="0" size="mini"/>
        </template>
      </el-table-column>
      <el-table-column label="单价" width="150">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.price" :min="0" size="mini"/>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.$index,scope.row)"/>
        </template>
      </el-table-column>
    </el-table>
    <br >
    <div>
      <el-button :loading="loading" type="primary" size="mini" @click="submitForm">提交</el-button>
      <el-button size="mini" @click="getAmount">计算价格</el-button>
      <el-button size="mini" @click="resetForm">重置</el-button>
    </div>
  </div>
</template>

<script>

import { getCommodities } from '@/api/commodity'
import { createCustormerOrder } from '@/api/order'
import { validateTel } from '@/utils/validate'

export default {
  data() {
    const checkTel = (rule, value, callback) => {
      if (!validateTel(value)) {
        callback(new Error('必须是11位合法手机号'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      selectValue: '',
      options: [],
      commodities: [],
      postForm: {
        orderType: '',
        customerSource: '',
        externalOrderId: '',
        customerName: '',
        customerTel: '',
        orderCreatedAt: '',
        deliveryAddress: '',
        deliveryAt: '',
        florist: null,
        remarks: '',
        amount: null,
        goods: []
      },
      rules: {
        orderType: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        customerSource: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        florist: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        remarks: [
          { max: 255, message: '最多255  个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      getCommodities().then(response => {
        console.log(response)
        this.options = response.map(item => {
          return {
            price: 0,
            number: 0,
            id: item.id,
            name: item.name,
            colour: item.colour,
            level: item.level,
            classify: item.classify
          }
        })
      })
    },
    submitForm() {
      this.$refs['postForm'].validate(valid => {
        if (valid) {
          const len = this.commodities.length
          if (len <= 0) {
            this.$message.error('请添加商品')
            return
          }
          for (let i = 0; i < len; i++) {
            if (this.commodities[i].Quantity <= 0) {
              this.$message.error('商品数量不能为 0')
              this.postForm.goods = []
              return
            }
            this.postForm.goods.push({
              id: this.commodities[i].id,
              price: this.commodities[i].price,
              number: this.commodities[i].number
            })
          }

          console.log(this.postForm)
          this.loading = true
          createCustormerOrder(this.postForm)
            .then(response => {
              console.log(response)
              this.loading = false
              this.resetForm()
              this.$message.success('创建成功')
            })
            .catch(() => {
              this.loading = false
              this.postForm.goods = []
              this.$message.error('创建失败')
            })
        }
      })
    },
    selectChange() {
      if (this.selectValue !== '') {
        this.selectValue.Disabled = true
        this.commodities.push(this.selectValue)
        this.selectValue = ''
      }
    },
    handleDelete(index, row) {
      this.commodities.splice(index, 1)
      row.Disabled = false
    },
    resetForm() {
      this.postForm.orderType = ''
      this.postForm.customerSource = ''
      this.postForm.externalOrderId = ''
      this.postForm.customerName = ''
      this.postForm.customerTel = ''
      this.postForm.orderCreatedAt = ''
      this.postForm.DeliveryAddress = ''
      this.postForm.deliveryAt = ''
      this.postForm.florist = null
      this.postForm.amount = null
      this.postForm.remarks = ''
      this.postForm.goods = []
      for (let i = 0; i < this.commodities.length; i++) {
        this.commodities[i].Disabled = false
      }

      this.commodities = []
    },
    getAmount() {
      this.postForm.amount = 0
      this.postForm.goods = []
      for (let i = 0; i < this.commodities.length; i++) {
        this.postForm.amount = Number(this.postForm.amount) + Number(this.commodities[i].number * this.commodities[i].price)
      }
    }
  }
}
</script>
