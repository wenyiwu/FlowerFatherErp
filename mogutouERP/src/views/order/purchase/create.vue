<template>
  <div class="app-container">
    <el-form v-enterToNext="true" ref="postForm" :model="postForm" :rules="rules" size="mini" status-icon>
      <el-form-item label="供应商" prop="provider">
        <el-input v-model.number="postForm.provider"/>
      </el-form-item>
      <el-form-item label="运费" prop="freight">
        <el-input-number v-model="postForm.freight"/>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="postForm.remarks"/>
      </el-form-item>
      <el-form-item label="订单创建时间" prop="createAt">
        <el-date-picker v-model="postForm.createdAt" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" tyle="width:185px ;"/>
      </el-form-item>
      <el-form-item label="订单总额(自动计算)" prop="Amount">
        <el-input v-model.number="postForm.amount" type="number" v-text="postForm.amount"/>
      </el-form-item>
    </el-form>

    <el-select v-model="selectValue" filterable clearable placeholder="请选择商品" size="mini" @change="selectChange">
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
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            circle
            @click="handleDelete(scope.$index,scope.row)"/>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <div>
      <el-button :loading="loading" type="primary" size="mini" @click="submitForm">提交</el-button>
      <el-button size="mini" @click="getAmount">计算价格</el-button>
      <el-button size="mini" @click="resetForm">重置</el-button>
    </div>
  </div>
</template>

<script>
import { getCommodities } from '@/api/commodity'
import { createPurchaseOrder } from '@/api/order'

export default {
  data() {
    return {
      loading: false,
      selectValue: '',
      options: [],
      commodities: [],
      goods: {
        id: '',
        number: ''
      },
      postForm: {
        provider: null,
        amount: null,
        remarks: '',
        freight: null,
        createdAt: '',
        goods: []
      },
      rules: {
        freight: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        provider: [
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
    func() {
      console.log('on enter')
      const DOM = event.target
      const nextDOM = DOM.nextElementSibling
      nextDOM.focus()
    },
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
          if (this.postForm.createdAt === '') {
            this.$message.error('请填写订单创建时间')
            return
          }
          for (let i = 0; i < len; i++) {
            if (this.commodities[i].number <= 0) {
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
          createPurchaseOrder(this.postForm)
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
      this.postForm.amount = null
      this.postForm.remarks = ''
      this.postForm.goods = []
      for (let i = 0; i < this.commodities.length; i++) {
        this.commodities[i].Disabled = false
      }
      this.commodities = []
    },
    getAmount() {
      this.postForm.amount = Number(this.postForm.freight)
      this.postForm.goods = []
      for (let i = 0; i < this.commodities.length; i++) {
        this.postForm.amount = Number(this.postForm.amount) + Number(this.commodities[i].number * this.commodities[i].price)
      }
    }
  }
}
</script>
