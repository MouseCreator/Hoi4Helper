package mouse.hoi.gamefiles.tempmodel.dynamic;

import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.common.annotation.SkipDeclaration;

import java.util.List;

@Block
@SkipDeclaration
public class ConditionBlock {
    @ObjField(text = "if")
    List<Condition> mainCondition;
    @ObjField(text = "else_if")
    List<Condition> elseConditions;
    @ObjField(text = "else")
    List<Condition> finalCondition;
}
